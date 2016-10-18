package com.guotion.pushserver.client.message;

import java.util.Map;

public abstract class NetMessage {

    public abstract void setState(int paramInt);

    public abstract void putData(String paramString, Object paramObject);

    public abstract int getState();

    public abstract Map getDatas();

    public abstract Object getData(String paramString);
}