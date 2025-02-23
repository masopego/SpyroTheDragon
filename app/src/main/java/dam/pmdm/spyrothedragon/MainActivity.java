package dam.pmdm.spyrothedragon;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;

import dam.pmdm.spyrothedragon.databinding.ActivityMainBinding;
import dam.pmdm.spyrothedragon.databinding.FragmentCharactersGuideBinding;
import dam.pmdm.spyrothedragon.databinding.FragmentWelcomeBinding;
import dam.pmdm.spyrothedragon.databinding.FragmentWorldsGuideBinding;
import dam.pmdm.spyrothedragon.utils.ViewAnimator;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    NavController navController = null;

    private FragmentWelcomeBinding welcomeBinding;
    private FragmentCharactersGuideBinding charactersGuideBinding;
    private FragmentWorldsGuideBinding worldsGuideBinding;

    private Boolean needToStartGuide = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        welcomeBinding = binding.includeLayout;
        charactersGuideBinding = binding.includeCharacterLayout;
        worldsGuideBinding = binding.includeWorldsLayout;

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
        View navigationView = binding.navView;




        bubble.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                // Obtener el ancho de la pantalla
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int screenWidth = displayMetrics.widthPixels;
                int heightPixels = displayMetrics.heightPixels;
// 1070
// Calcular el ancho de la burbuja
                int bubbleWidth = v.getWidth();
                int menuButtonWidth = screenWidth / 3;

                int firstPosition = (menuButtonWidth/2) - bubbleWidth/2;
                int secondPosition = menuButtonWidth+(menuButtonWidth/2) -bubbleWidth/2;
                int thirdPosition = menuButtonWidth*2+(menuButtonWidth/2)- bubbleWidth/2;
                // Elimina el listener si solo necesitas obtenerlo una vez
                bubble.removeOnLayoutChangeListener(this);

                float bubbleX = secondPosition; // O usa firstPosition o thirdPosition según sea necesario

// La burbuja sigue estando en la parte inferior, solo ajustamos X.
                bubble.setX(bubbleX);
                bubble.setY(heightPixels-(bubble.getHeight()/3*2));
            }
        });

        // Configurar botón "Siguiente"
        charactersGuideBinding.btnNext.setOnClickListener(v -> initializeNavigationGuide2());
    }

    private int[] getViewPosition(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location;
    }

    private void initializeNavigationGuide2() {
        navController.navigate(R.id.navigation_worlds);
        charactersGuideBinding.characterGuideLayout.setVisibility(View.GONE);
        worldsGuideBinding.worldsGuideLayout.setVisibility(View.VISIBLE);
        worldsGuideBinding.btnNext.setOnClickListener(v->initializeNavigationGuide3());
    }

    private void initializeNavigationGuide3() {
        worldsGuideBinding.worldsGuideLayout.setVisibility(View.GONE);
        navController.navigate(R.id.navigation_collectibles);
    }

    private void onExitGuide(View view) {
        needToStartGuide = false;
        welcomeBinding.guideLayout.setVisibility(View.GONE);
        charactersGuideBinding.characterGuideLayout.setVisibility(View.GONE);
        worldsGuideBinding.worldsGuideLayout.setVisibility(View.GONE);
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
        // Infla el menú
        getMenuInflater().inflate(R.menu.about_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Gestiona el clic en el ítem de información
        if (item.getItemId() == R.id.action_info) {
            showInfoDialog();  // Muestra el diálogo
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showInfoDialog() {
        // Crear un diálogo de información
        new AlertDialog.Builder(this)
                .setTitle(R.string.title_about)
                .setMessage(R.string.text_about)
                .setPositiveButton(R.string.accept, null)
                .show();
    }



}