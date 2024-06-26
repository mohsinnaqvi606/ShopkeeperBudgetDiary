package com.naqvi.shopkeeperbudgetdiary.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.naqvi.shopkeeperbudgetdiary.Activities.Add_Product_Activity;
import com.naqvi.shopkeeperbudgetdiary.Activities.Edit_Product_Activity;
import com.naqvi.shopkeeperbudgetdiary.R;

public class MapsFragment extends Fragment {

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng pakistan = new LatLng(36, 73);
// googleMap.addMarker(new MarkerOptions().position(pakistan).title("Marker in Pakistan"));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pakistan, 13));

            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng point) {
                    googleMap.clear();
                    MarkerOptions marker = new MarkerOptions()
                            .position(new LatLng(point.latitude, point.longitude));
                    googleMap.addMarker(marker);
                    System.out.println(point.latitude + "---" + point.longitude);

                    ((Add_Product_Activity) getActivity()).setLatLng(point);
                }
            });
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}