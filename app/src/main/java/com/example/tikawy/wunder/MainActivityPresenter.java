package com.example.tikawy.wunder;

public class MainActivityPresenter {

    private Model model;
    private View view;

    public MainActivityPresenter(View view){
        this.model = new Model();
        this.view = view;
    }

    /**
     * Interface with abstract methods for the view
     */
    public interface View{
        void displayListView();
        void displayCarLocation();
    }
}
