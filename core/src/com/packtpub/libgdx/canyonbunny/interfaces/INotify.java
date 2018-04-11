package com.packtpub.libgdx.canyonbunny.interfaces;


/**
 * 用于和 平台相关的沟通，一个接口，不同平台可以有不同的实现。
 */
public interface INotify {
    void display();
    String getDataBaseFileName();
}