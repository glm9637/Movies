package com.example.android.movies.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by glm9637 on 18.03.2018 17:13.
 */

/**
 * Basic Model for the cast data of a Movie
 */
public class Cast {

    private int mCastId;
    private String mCharacter;
    private String mGender;
    private String mName;
    private String mProfilePath;

    /**
     * create a new Cast Object from a jsonValue
     *
     * @param jsonCast one JsonObject parsed from the returned Value for a cast query
     */
    public Cast(JSONObject jsonCast){
        try {
            mCastId = jsonCast.getInt("cast_id");
            mCharacter = jsonCast.getString("character");
            switch (jsonCast.getInt("gender")){
                case 0:
                    mGender = "Not Specified";
                    break;
                case 1:
                    mGender = "Female";
                    break;
                case 2:
                    mGender = "Male";
                    break;
            }
            mName = jsonCast.getString("name");
            mProfilePath = jsonCast.getString("profile_path");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getCastID(){
        return mCastId;
    }

    public String getCharacter() {
        return mCharacter;
    }

    public String getGender() {
        return mGender;
    }

    public String getName() {
        return mName;
    }

    public String getProfilePath() {
        return mProfilePath;
    }
}
