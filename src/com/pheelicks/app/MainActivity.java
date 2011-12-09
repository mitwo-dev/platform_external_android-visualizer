/**
 * Copyright 2011, Felix Palmer
 *
 * Licensed under the MIT license:
 * http://creativecommons.org/licenses/MIT/
 */
package com.pheelicks.app;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import com.pheelicks.visualizer.R;
import com.pheelicks.visualizer.VisualizerView;
import com.pheelicks.visualizer.renderer.BarGraphRenderer;
import com.pheelicks.visualizer.renderer.CircleRenderer;
import com.pheelicks.visualizer.renderer.LineRenderer;

/**
 * Basic demo to show how to use VisualizerView
 *
 */
public class MainActivity extends Activity {
  private MediaPlayer mPlayer;
  private VisualizerView mVisualizerView;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    init();
  }

  private void init()
  {
    mPlayer = MediaPlayer.create(this, R.raw.test);
    mPlayer.setLooping(true);
    mPlayer.start();

    mVisualizerView = (VisualizerView) findViewById(R.id.visualizerView);
    mVisualizerView.link(mPlayer);

    // Start with just line renderer
    addLineRenderer();
  }

  private void addBarGraphRenderers()
  {
    Paint paint = new Paint();
    paint.setStrokeWidth(50f);
    paint.setAntiAlias(true);
    paint.setColor(Color.argb(200, 233, 0, 44));
    BarGraphRenderer barGraphRendererBottom = new BarGraphRenderer(16, paint, false);
    mVisualizerView.addRenderer(barGraphRendererBottom);

    Paint paint2 = new Paint();
    paint2.setStrokeWidth(12f);
    paint2.setAntiAlias(true);
    paint2.setColor(Color.argb(200, 11, 111, 233));
    BarGraphRenderer barGraphRendererTop = new BarGraphRenderer(4, paint2, true);
    mVisualizerView.addRenderer(barGraphRendererTop);
  }

  private void addCircleRenderer()
  {
    Paint paint3 = new Paint();
    paint3.setStrokeWidth(3f);
    paint3.setAntiAlias(true);
    paint3.setColor(Color.argb(255, 222, 92, 143));
    CircleRenderer circleRenderer = new CircleRenderer(paint3, true);
    mVisualizerView.addRenderer(circleRenderer);
  }

  private void addLineRenderer()
  {
    Paint linePaint = new Paint();
    linePaint.setStrokeWidth(1f);
    linePaint.setAntiAlias(true);
    linePaint.setColor(Color.argb(88, 0, 128, 255));

    Paint lineFlashPaint = new Paint();
    lineFlashPaint.setStrokeWidth(5f);
    lineFlashPaint.setAntiAlias(true);
    lineFlashPaint.setColor(Color.argb(188, 255, 255, 255));
    LineRenderer lineRenderer = new LineRenderer(linePaint, lineFlashPaint, true);
    mVisualizerView.addRenderer(lineRenderer);
  }

  // Cleanup
  private void cleanUp()
  {
    if (mPlayer != null)
    {
      mVisualizerView.release();
      mPlayer.release();
      mPlayer = null;
    }
  }

  @Override
  protected void onResume()
  {
    super.onResume();
    if(mPlayer == null)
    {
      init();
    }
    else
    {
      mPlayer.start();
    }
  }

  @Override
  protected void onPause()
  {
    if (isFinishing())
    {
      cleanUp();
    }
    else
    {
      mPlayer.pause();
    }

    super.onPause();
  }

  @Override
  protected void onDestroy()
  {
    cleanUp();
    super.onDestroy();
  }

  // Actions for buttons defined in xml
  public void startPressed(View view)
  {
    mPlayer.start();
  }

  public void stopPressed(View view)
  {
    mPlayer.stop();
  }

  public void barPressed(View view)
  {
    mVisualizerView.clearRenderers();
    addBarGraphRenderers();
  }

  public void circlePressed(View view)
  {
    mVisualizerView.clearRenderers();
    addCircleRenderer();
  }

  public void linePressed(View view)
  {
    mVisualizerView.clearRenderers();
    addLineRenderer();
  }

  public void allPressed(View view)
  {
    mVisualizerView.clearRenderers();
    addBarGraphRenderers();
    addCircleRenderer();
    addLineRenderer();
  }
}