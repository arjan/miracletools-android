package nl.miraclethings.tools.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by arjan on 29-2-16.
 */
public class PrefsStore {

    private final Gson gson;
    private final HashMap<String, Object> localCache;
    private Context context;

    private static PrefsStore instance;

    public static PrefsStore getInstance(Context context) {
        if (instance == null) {
            instance = new PrefsStore(context.getApplicationContext());
        } else {
            instance.context = context;
        }
        return instance;
    }

    private PrefsStore(Context context) {
        this.context = context;
        this.gson = new Gson();
        this.localCache = new HashMap<>();
    }

    public <T> T get(String key, Class<T> cls) {
        if (localCache.containsKey(key)) {
            return (T) localCache.get(key);
        }
        SharedPreferences prefs = getPrefs();
        String value = prefs.getString(key, null);
        if (value == null) {
            return null;
        }
        T result = this.gson.fromJson(value, cls);
        localCache.put(key, result);
        return result;
    }

    public void put(String key, Object value) {
        set(key, value);
    }

    public void set(String key, Object value) {
        SharedPreferences prefs = getPrefs();
        prefs.edit().putString(key, this.gson.toJson(value)).apply();
    }

    public void clear() {
        localCache.clear();
        getPrefs().edit().clear().commit();
    }

    private SharedPreferences getPrefs() {
        return context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
    }

    public <T> T get(String key, Class<T> cls, T defaultValue) {
        T value = get(key, cls);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }
}
