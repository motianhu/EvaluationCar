package com.smona.app.evaluationcar.data.model;

/**
 * Created by Moth on 2017/3/23.
 */

public class ResModel<T> {
    public boolean sucess;
    public String message;
    public T object;

    public String toString() {
        return "sucess=" + sucess + ";message=" + message + ";object=" + object;
    }
}
