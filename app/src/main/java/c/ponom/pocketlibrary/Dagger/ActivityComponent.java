package c.ponom.pocketlibrary.Dagger;


import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MyActivity myActivity);

}