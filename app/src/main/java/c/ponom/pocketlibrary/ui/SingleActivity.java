package c.ponom.pocketlibrary.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import c.ponom.pocketlibrary.R;
import c.ponom.pocketlibrary.data.Repository;
import c.ponom.pocketlibrary.data.network_loaders.NetworkLoaders;
import c.ponom.pocketlibrary.data.room_entities.Author;
import c.ponom.pocketlibrary.data.room_entities.Book;
import c.ponom.pocketlibrary.data.room_entities.SubChapter;
import c.ponom.pocketlibrary.di.DIСlass;
import c.ponom.pocketlibrary.ui.fragments.AuthorsListFragment;
import c.ponom.pocketlibrary.ui.fragments.BaseFragment;
import c.ponom.pocketlibrary.ui.fragments.BooksListFragment;
import c.ponom.pocketlibrary.ui.fragments.InfoFragment;
import c.ponom.pocketlibrary.ui.fragments.LoadedListFragment;
import c.ponom.pocketlibrary.ui.fragments.SubChaptersFragment;
import c.ponom.pocketlibrary.ui.web_view.WebViewFragment;

public class SingleActivity extends AppCompatActivity implements BaseFragment.ActivityUiOptions {


    Repository repository;
    public Toolbar toolbar=null;
    ProgressDialog pd;
    ProgressBar progressBar;
    private boolean menuSendVisible=false;
    public String currentBookUrl="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pd = new ProgressDialog(this);
        setContentView(R.layout.start_screen);
        repository= DIСlass.getRepository();
        progressBar =  findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
        toolbar =findViewById(R.id.main_toolbar);
        toolbar.inflateMenu(R.menu.menus);
        toolbar.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return onOptionsItemSelected(item);
                    }
                });


         toolbar.setTitle("");


        setSupportActionBar(toolbar);
        hideSendButton();

        if (savedInstanceState==null) {
            // todo - вообще это потенциально глючно, проверить что будет если запустить
            //  пустое приложение без сети, потом восстановить его при включенной сети - вроде тогда загрузка
            //  не вызовется и спиок будет пустой до перезапуска.
            NetworkLoaders.loadChapterList();
            showMainFragment();
        }

    }



    @Override
    protected void onResume() {
        super.onResume();
        toolbar.setTitle("");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menus, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.send).setVisible(menuSendVisible);
        menu.findItem(R.id.send).setEnabled(menuSendVisible);
        return super.onPrepareOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case android.R.id.home: {
                super.onBackPressed();
                break;
            }

            case R.id.action_about_item: { // todo - вызов информационного окна, в тулбар добавить в разметку
                break;
            }

            case R.id.action_loaded_book_list: {
                showLoadedListFragment();
                break;
            }

            case R.id.send: {
                sendLink(currentBookUrl);
                break;


            }



        }
        return super.onOptionsItemSelected(item);
    }







    private void sendLink(String bookUrl) {
        if (bookUrl.isEmpty()) return;
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT,bookUrl);
        shareIntent.setType("text/plain");
        this.startActivity(Intent.createChooser(shareIntent,
                this.getResources().getText(R.string.sendTo)));

    }


    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof BaseFragment) {

            ((BaseFragment) fragment).setActivityUiOptionsCallback(this);
        }
    }

    @Override
    public void showProgressBar () {
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgressBar () {
        progressBar.setVisibility(View.INVISIBLE);
    }


    @Override
    public void showSendButton() {
        menuSendVisible=true;
   }

    @Override
    public void hideSendButton() {
        menuSendVisible=false;
    }





    @Override
    public void setNewTitle(String title, String subtitle){
        if (toolbar == null) {
            return;
        }
        toolbar.setTitle(title);
        toolbar.setSubtitle(subtitle);

    }

    @Override
    public void setBackButtonVisibility(boolean status) {


        Objects.requireNonNull(getSupportActionBar()).
                setDisplayHomeAsUpEnabled(status);
        getSupportActionBar().setHomeButtonEnabled(status);
    }

    public void showAuthorsListForSubChapter(SubChapter subChapter) {


        Fragment authorsFragment = AuthorsListFragment.newInstance(subChapter,this);
        getSupportFragmentManager().beginTransaction().replace(R.id.mainViewPager,authorsFragment)
                .addToBackStack(null)
                .commit();

    }


    public void showBooksForSubChapterAuthor(Author author) {


        Fragment bookFragment = BooksListFragment.newInstance(author,this);

        getSupportFragmentManager().beginTransaction().replace(R.id.mainViewPager,bookFragment)
                .addToBackStack(null)
                .commit();

    }


    public  void  launchWebView(String filePath, Book book){

        Fragment bookFragment = WebViewFragment.newInstance(book,filePath,this);

        getSupportFragmentManager().beginTransaction().replace(R.id.mainViewPager,bookFragment)
                .addToBackStack(null)
                .commit();

    }


    public void showMainFragment() {
        final Fragment fragment = SubChaptersFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.mainViewPager,fragment)
                .commit();
    }

    public void showLoadedListFragment() {
        final Fragment fragment = LoadedListFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.mainViewPager,fragment)
                .addToBackStack(null)
                .commit();
    }
    public void showInfo() {
        final Fragment fragment = InfoFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.mainViewPager,fragment)
                .addToBackStack(null)
                .commit();
    }


}


