package dam.pmdm.spyrothedragon;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import dam.pmdm.spyrothedragon.databinding.ActivityMainBinding;
import dam.pmdm.spyrothedragon.databinding.FragmentCharactersGuideBinding;
import dam.pmdm.spyrothedragon.databinding.FragmentCollectiblesGuideBinding;
import dam.pmdm.spyrothedragon.databinding.FragmentWelcomeBinding;
import dam.pmdm.spyrothedragon.databinding.FragmentWorldsGuideBinding;
import dam.pmdm.spyrothedragon.utils.BubbleCoordinates;
import dam.pmdm.spyrothedragon.utils.BubblePosition;
import dam.pmdm.spyrothedragon.utils.BubblePositionCalculator;
import dam.pmdm.spyrothedragon.utils.ViewAnimator;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    NavController navController = null;

    private FragmentWelcomeBinding welcomeBinding;
    private FragmentCharactersGuideBinding charactersGuideBinding;
    private FragmentWorldsGuideBinding worldsGuideBinding;
    private FragmentCollectiblesGuideBinding collectibleGuideBinding;

    private Boolean needToStartGuide = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        welcomeBinding = binding.includeLayout;
        charactersGuideBinding = binding.includeCharacterLayout;
        worldsGuideBinding = binding.includeWorldsLayout;
        collectibleGuideBinding = binding.includeCollectibleLayout;


        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.navHostFragment);
        if (navHostFragment != null) {
            navController = NavHostFragment.findNavController(navHostFragment);
            NavigationUI.setupWithNavController(binding.navView, navController);
            NavigationUI.setupActionBarWithNavController(this, navController);

        }

        binding.navView.setOnItemSelectedListener(this::selectedBottomMenu);


        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.navigation_characters ||
                    destination.getId() == R.id.navigation_worlds ||
                    destination.getId() == R.id.navigation_collectibles) {
                // Para las pantallas de los tabs, no queremos que aparezca la flecha de atrás
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }
            else {
                // Si se navega a una pantalla donde se desea mostrar la flecha de atrás, habilítala
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        });

        initializeGuide();

    }

    private void initializeGuide() {
        if (needToStartGuide){
            initializeWelcomeStep();
        }
    }

    private void initializeWelcomeStep() {
        welcomeBinding.exitGuide.setOnClickListener(this::onExitGuide);
        welcomeBinding.guideLayout.setVisibility(View.VISIBLE);

        ImageView spyroImage = welcomeBinding.spyroTitleImage;
        ImageButton btnStart = welcomeBinding.btnStart;

        // Create animations
        ViewAnimator.animateTranslationX(spyroImage, -50f, 50f, 3000);
        ViewAnimator.animateTranslationY(btnStart, -15f, 15f, 1000);

        welcomeBinding.btnStart.setOnClickListener(v ->initializeNavigationGuide());
    }

    private void initializeNavigationGuide() {
        welcomeBinding.guideLayout.setVisibility(View.GONE);
        charactersGuideBinding.exitGuide.setOnClickListener(this::onExitGuide);
        charactersGuideBinding.characterGuideLayout.setVisibility(View.VISIBLE);

        ImageView bubble = charactersGuideBinding.bubble;
        ViewAnimator.animateAlpha(bubble, 0f, 0.5f, 1000);

        bubble.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

                BubbleCoordinates position = new BubblePositionCalculator(v, bubble, getWindowManager()).getPosition(BubblePosition.CHARACTERS);

                bubble.setX(position.getX());
                bubble.setY(position.getY());
                bubble.removeOnLayoutChangeListener(this);

            }
        });

        charactersGuideBinding.btnNext.setOnClickListener(v -> initializeNavigationGuide2());
    }

    private void initializeNavigationGuide2() {
        navController.navigate(R.id.navigation_worlds);
        charactersGuideBinding.characterGuideLayout.setVisibility(View.GONE);
        worldsGuideBinding.worldsGuideLayout.setVisibility(View.VISIBLE);

        ImageView bubble = worldsGuideBinding.bubble;
        ViewAnimator.animateAlpha(bubble, 0f, 0.5f, 1000);


        bubble.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

                BubbleCoordinates position = new BubblePositionCalculator(v, bubble, getWindowManager()).getPosition(BubblePosition.WORLD);

                bubble.setX(position.getX());
                bubble.setY(position.getY());
                bubble.removeOnLayoutChangeListener(this);

            }
        });

        worldsGuideBinding.btnNext.setOnClickListener(v->initializeNavigationGuide3());
    }

    private void initializeNavigationGuide3() {
        navController.navigate(R.id.navigation_collectibles);
        worldsGuideBinding.worldsGuideLayout.setVisibility(View.GONE);
        collectibleGuideBinding.collectiblesGuideLayout.setVisibility(View.VISIBLE);

        ImageView bubble = collectibleGuideBinding.bubble;
        ViewAnimator.animateAlpha(bubble, 0f, 0.5f, 1000);


        bubble.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

                BubbleCoordinates position = new BubblePositionCalculator(v, bubble, getWindowManager()).getPosition(BubblePosition.COLLECTIBLES);

                bubble.setX(position.getX());
                bubble.setY(position.getY());
                bubble.removeOnLayoutChangeListener(this);

            }
        });

        collectibleGuideBinding.btnNext.setOnClickListener(v->initializeNavigationGuide4());
    }

    private void initializeNavigationGuide4() {
        collectibleGuideBinding.collectiblesGuideLayout.setVisibility(View.GONE);
    }

    private void onExitGuide(View view) {
        needToStartGuide = false;
        welcomeBinding.guideLayout.setVisibility(View.GONE);
        charactersGuideBinding.characterGuideLayout.setVisibility(View.GONE);
        worldsGuideBinding.worldsGuideLayout.setVisibility(View.GONE);
        collectibleGuideBinding.collectiblesGuideLayout.setVisibility(View.GONE);
    }


    private boolean selectedBottomMenu(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.nav_characters)
            navController.navigate(R.id.navigation_characters);
        else
        if (menuItem.getItemId() == R.id.nav_worlds)
            navController.navigate(R.id.navigation_worlds);
        else
            navController.navigate(R.id.navigation_collectibles);
        return true;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.about_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_info) {
            showInfoDialog();  // Muestra el diálogo
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showInfoDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.title_about)
                .setMessage(R.string.text_about)
                .setPositiveButton(R.string.accept, null)
                .show();
    }



}