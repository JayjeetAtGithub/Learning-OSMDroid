package com.phdetector.jayjeet.myapplication;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.osmdroid.bonuspack.kml.KmlDocument;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Polygon;
import org.osmdroid.views.overlay.simplefastpoint.LabelledGeoPoint;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity {

    private MapView map;
    private KmlDocument kmlDocument;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        map = (MapView) findViewById(R.id.map_view);
        map.setTileSource(TileSourceFactory.MAPNIK);
        kmlDocument = new KmlDocument();

        // Get 3 points from add marker screen
        List<GeoPoint> geoPoints = new ArrayList<>();
        geoPoints.add(new LabelledGeoPoint(82,54,"p1"));
        geoPoints.add(new LabelledGeoPoint(70,60,"p2"));
        geoPoints.add(new LabelledGeoPoint(95,100,"p3"));
        Polygon polygon = new Polygon();
        polygon.setFillColor(Color.argb(75, 255,0,0));
        geoPoints.add(geoPoints.get(0));
        polygon.setPoints(geoPoints);
        polygon.setTitle("A sample polygon");

        List<List<GeoPoint>> holes = new ArrayList<>();
        holes.add(geoPoints);
        polygon.setHoles(holes);

        map.getOverlayManager().add(polygon);

        // Saves the polygon in a KML File
        kmlDocument.mKmlRoot.addOverlay(polygon,kmlDocument);
        File localFile = kmlDocument.getDefaultPathForAndroid("my_area.kml");
        Log.d("path",localFile.getAbsolutePath());
        kmlDocument.saveAsKML(localFile);

    }
}
