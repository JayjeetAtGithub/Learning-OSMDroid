package com.phdetector.jayjeet.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.kml.KmlDocument;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polygon;
import org.osmdroid.views.overlay.Polyline;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements MapEventsReceiver {

    private MapView map;
    private MapEventsOverlay mapEventsOverlay;
    private KmlDocument kmlDocument;
    private List<GeoPoint> geoPoints;
    private Polyline polyline;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Saves the polygon in a KML File
                kmlDocument.mKmlRoot.addOverlay(polyline,kmlDocument);
                long millis = System.currentTimeMillis();
                File localFile = kmlDocument.getDefaultPathForAndroid("KML_" + millis + ".kml");
                Log.d("path",localFile.getAbsolutePath());
                kmlDocument.saveAsKML(localFile);
                Toast.makeText(getApplicationContext(),"Polyline Saved",Toast.LENGTH_SHORT).show();
            }
        });

        map = (MapView) findViewById(R.id.map_view);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
        IMapController mapController = map.getController();
        mapController.setZoom(13);
        GeoPoint startPoint = new GeoPoint(23.5204,87.3119);
        mapController.setCenter(startPoint);

        kmlDocument = new KmlDocument();

        mapEventsOverlay = new MapEventsOverlay(this, this);
        map.getOverlays().add(0, mapEventsOverlay);

        geoPoints = new ArrayList<>();
        polyline = new Polyline();

    }

    @Override
    public boolean singleTapConfirmedHelper(GeoPoint p) {
        geoPoints.add(p);
        polyline.setPoints(geoPoints);
        Marker marker = new Marker(map);
        marker.setPosition(p);
        //marker.setIcon(getResources().getDrawable(R.mipmap.marker_red_round));
        marker.setAnchor(Marker.ANCHOR_CENTER,Marker.ANCHOR_BOTTOM);
        marker.setTitle(String.valueOf(p.getLatitude() + "," + p.getLongitude()));
        map.getOverlays().add(marker);
        map.getOverlayManager().add(polyline);
        map.invalidate();
        return true;
    }

    @Override
    public boolean longPressHelper(GeoPoint p) {
        return false;
    }

}

