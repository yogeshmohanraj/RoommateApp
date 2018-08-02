/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class ParseConnection extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    // Parse username: user, password: KtO2CrXj8Viv

    // Enable Local Datastore.
    Parse.enableLocalDatastore(this);

    // Add your initialization code here
    Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
            .applicationId("664e66602ce03e5bc0cc044177eea9c2fea88cb0")
            .clientKey("a9113541682a468409f39b8fde133bb960c0af55")
            .server("http://18.217.95.118:80/parse/")
            .build()
    );

    //ParseUser.getCurrentUser().logOut();

    ParseUser.enableAutomaticUser();

    ParseACL defaultACL = new ParseACL();
    defaultACL.setPublicReadAccess(true);
    defaultACL.setPublicWriteAccess(true);
    ParseACL.setDefaultACL(defaultACL, true);

  }
}
