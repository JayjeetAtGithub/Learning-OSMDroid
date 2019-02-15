package com.phdetector.jayjeet.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.osmdroid.bonuspack.kml.KmlDocument;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.FolderOverlay;

import java.io.File;
import java.net.URL;
import java.util.Map;

public class RenderMapActivity extends AppCompatActivity {

    private String absPath;
    private MapView map;
    private KmlDocument kmlDocument;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_render_map);

        kmlDocument = new KmlDocument();
        map = (MapView) findViewById(R.id.render_map_view);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        Intent intent = getIntent();
        absPath = intent.getStringExtra("kml_file_uri");
        Log.d("PATH",absPath);
        File file = new File(absPath);
        kmlDocument.parseKMLFile(file);

        FolderOverlay kmlOverlay = (FolderOverlay)kmlDocument.mKmlRoot.buildOverlay(map, null, null, kmlDocument);
        map.getOverlays().add(kmlOverlay);
        map.invalidate();

    }
}
