package com.pszumanski.libraryregister.ui;


import com.github.spring.boot.javafx.stereotype.ViewController;
import com.github.spring.boot.javafx.view.ViewLoader;

@ViewController
public class MenuController {
    private ViewLoader viewLoader;


    public void loadView() {
        viewLoader.load("mainView.fmxl");
    }
}
