package com.likeitsmp.thirparauth.data;

import java.io.Serializable;
import java.util.Objects;

public final class UserData implements Serializable
{
    private String _password;

    public UserData(String password)
    {
        setPassword(password);
    }

    public void setPassword(String password)
    {
        Objects.requireNonNull(password);
        _password = password;
    }

    public boolean verifies(String password)
    {
        return _password.equals(password);
    }
}
