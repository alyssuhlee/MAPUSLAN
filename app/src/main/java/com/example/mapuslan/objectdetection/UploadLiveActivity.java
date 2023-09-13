package com.example.mapuslan.objectdetection;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import org.pytorch.IValue;
import org.pytorch.LiteModuleLoader;
import org.pytorch.Module;
import org.pytorch.Tensor;
//import org.pytorch.demo.objectdetection.R;
import com.example.mapuslan.R;

import org.pytorch.torchvision.TensorImageUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class UploadLiveActivity extends AppCompatActivity implements Runnable {

    private ImageView mImageView;
    private ResultView mResultView;
    private Button mButtonDetect;
    private ProgressBar mProgressBar;
    private Bitmap mBitmap = null;
    private Module mModule = null;
    private float mImgScaleX, mImgScaleY, mIvScaleX, mIvScaleY, mStartX, mStartY;

    public static String assetFilePath(Context context, String assetName) throws IOException {
        File file = new File(context.getFilesDir(), assetName);
        if (file.exists() && file.length() > 0) {
            return file.getAbsolutePath();
        }

        try (InputStream is = context.getAssets().open(assetName)) {
            try (OutputStream os = new FileOutputStream(file)) {
                byte[] buffer = new byte[4 * 1024];
                int read;
                while ((read = is.read(buffer)) != -1) {
                    os.write(buffer, 0, read);
                }
                os.flush();
            }
            return file.getAbsolutePath();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        }

        setContentView(R.layout.activity_upload_live);

        mImageView = findViewById(R.id.imageView);
        mImageView.setImageBitmap(mBitmap);
        mResultView = findViewById(R.id.resultView);
        mResultView.setVisibility(View.INVISIBLE);

        final Button buttonSelect = findViewById(R.id.selectButton);
        buttonSelect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mResultView.setVisibility(View.INVISIBLE);

                final CharSequence[] options = { "Choose from Photos", "Take Picture", "Cancel" };
                AlertDialog.Builder builder = new AlertDialog.Builder(UploadLiveActivity.this);
                builder.setTitle("Choose an option");

                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Take Picture")) {
                            Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(takePicture, 0);
                        }
                        else if (options[item].equals("Choose from Photos")) {
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            pickPhoto.setType("image/*");   // select only pictures
                            startActivityForResult(pickPhoto , 1);
                        }
                        else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });

        mButtonDetect = findViewById(R.id.detectButton);

        mButtonDetect.setEnabled(false);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mButtonDetect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mButtonDetect.setEnabled(true);
                mProgressBar.setVisibility(ProgressBar.VISIBLE);
                mButtonDetect.setText(getString(R.string.run_model));

                mImgScaleX = (float) mBitmap.getWidth() / PrePostProcessor.mInputWidth;
                mImgScaleY = (float) mBitmap.getHeight() / PrePostProcessor.mInputHeight;

                mIvScaleX = (mBitmap.getWidth() > mBitmap.getHeight() ? (float) mImageView.getWidth() / mBitmap.getWidth() : (float) mImageView.getHeight() / mBitmap.getHeight());
                mIvScaleY = (mBitmap.getHeight() > mBitmap.getWidth() ? (float) mImageView.getHeight() / mBitmap.getHeight() : (float) mImageView.getWidth() / mBitmap.getWidth());

                mStartX = (mImageView.getWidth() - mIvScaleX * mBitmap.getWidth()) / 2;
                mStartY = (mImageView.getHeight() - mIvScaleY * mBitmap.getHeight()) / 2;

                Thread thread = new Thread(UploadLiveActivity.this);
                thread.start();
            }
        });

        try {
            mModule = LiteModuleLoader.load(UploadLiveActivity.assetFilePath(getApplicationContext(), "v1.torchscript.ptl"));
            BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open("customclasses.txt")));
            String line;
            List<String> classes = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                classes.add(line);
            }
            PrePostProcessor.mClasses = new String[classes.size()];
            classes.toArray(PrePostProcessor.mClasses);
        } catch (IOException e) {
            Log.e("Object Detection", "Error reading assets", e);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Log.e(TAG, "Taking a picture using camera");
                        mBitmap = (Bitmap) data.getExtras().get("data");
                        Matrix matrix = new Matrix();
                        mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);
                        mImageView.setImageBitmap(mBitmap);
                        mButtonDetect.setEnabled(true);
                    }
                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Log.e(TAG, "Selecting picture in gallery");
                        Uri selectedImage = data.getData();

                        Bitmap bitmap = null;
                        InputStream inputStream = null;
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                            inputStream = getContentResolver().openInputStream(selectedImage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (bitmap != null) {
                            Matrix matrix = new Matrix();
                            try {
                                // adjust the orientation of uploaded image accordingly to its correct orientation before showing at the image view
                                ExifInterface exifReader = new ExifInterface(inputStream);

                                // get the orientation of the uploaded image
                                int orientation = exifReader.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
                                if (orientation == ExifInterface.ORIENTATION_NORMAL) {
                                    // do nothing since the image is in its correct orientation
                                } else if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                                    matrix.postRotate(90);  // rotate 90 degrees clockwise
                                } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                                    matrix.postRotate(180);
                                } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                                    matrix.postRotate(270);
                                }
                                mBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                                mImageView.setImageBitmap(mBitmap);
                                mButtonDetect.setEnabled(true);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            // Failed to retrieve the bitmap, handle the error
                            System.out.println("Failed to get the bitmap");
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public void run() {
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(mBitmap, PrePostProcessor.mInputWidth, PrePostProcessor.mInputHeight, true);
        final Tensor inputTensor = TensorImageUtils.bitmapToFloat32Tensor(resizedBitmap, PrePostProcessor.NO_MEAN_RGB, PrePostProcessor.NO_STD_RGB);
        IValue[] outputTuple = mModule.forward(IValue.from(inputTensor)).toTuple();
        final Tensor outputTensor = outputTuple[0].toTensor();
        final float[] outputs = outputTensor.getDataAsFloatArray();
        final ArrayList<Result> results =  PrePostProcessor.outputsToNMSPredictions(outputs, mImgScaleX, mImgScaleY, mIvScaleX, mIvScaleY, mStartX, mStartY);

        runOnUiThread(() -> {
            mButtonDetect.setEnabled(true);
            mButtonDetect.setText(getString(R.string.detect));
            mProgressBar.setVisibility(ProgressBar.INVISIBLE);
            mResultView.setResults(results);
            mResultView.invalidate();
            mResultView.setVisibility(View.VISIBLE);
        });
    }
}
