package com.packtpub.libgdx.canyonbunny.desktop;

import com.packtpub.libgdx.canyonbunny.interfaces.INotify;
import com.packtpub.libgdx.canyonbunny.utils.Logs;

/**
 * Created by user on 4/11/18.
 */

public class MyClass implements INotify {
    @Override
    public void display() {
        Logs.d("anxii","I am imp by desktop");
    }
}
