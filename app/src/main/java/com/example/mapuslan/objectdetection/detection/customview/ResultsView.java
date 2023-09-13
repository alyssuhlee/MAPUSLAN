package com.example.mapuslan.objectdetection.detection.customview;

import com.example.mapuslan.objectdetection.detection.tflite.Classifier;

import java.util.List;

public interface ResultsView {
  public void setResults(final List<Classifier.Recognition> results);
}
