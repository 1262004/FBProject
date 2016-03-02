/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.fresher.za.model;

import java.io.Serializable;
import org.json.simple.JSONObject;

/**
 *
 * @author baonh2
 */
public abstract class BaseModel  implements Serializable {
    
    public JSONObject toJSON() {
        return new JSONObject();
    }
}
