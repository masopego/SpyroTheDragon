package dam.pmdm.spyrothedragon.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import dam.pmdm.spyrothedragon.databinding.FragmentWelcomeBinding;
import dam.pmdm.spyrothedragon.utils.ViewAnimator;

public class WelcomeFragment extends Fragment {

    private FragmentWelcomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentWelcomeBinding.inflate(inflater, container, false);

        ImageView spyroImage = binding.spyroTitleImage;
        ImageButton btnStart = binding.btnStart;

        // Create animations
        ViewAnimator.animateTranslationX(spyroImage, -50f, 50f, 3000);
        ViewAnimator.animateTranslationY(btnStart, -15f, 15f, 1000);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
