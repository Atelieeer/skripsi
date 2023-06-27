package id.kharisma.studio.atelier;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class SettingsFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView txtNamaUser, txtNoHpUser;
        FirebaseAuth mFirebaseAuth;
        mFirebaseAuth = FirebaseAuth.getInstance();
        ConstraintLayout btnLogout = (ConstraintLayout) view.findViewById(R.id.btnLogout);
        txtNamaUser = (TextView) view.findViewById(R.id.txtNamaUser);
        txtNoHpUser = (TextView) view.findViewById(R.id.txtNoHpUser);
        String namaUser = MemoryData.getName(getActivity());
        String noHpUser = MemoryData.getData(getActivity());
        txtNamaUser.setText(namaUser);
        txtNoHpUser.setText(noHpUser);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAuth.signOut();
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
            }
        });
    }
}