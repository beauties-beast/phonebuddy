package com.github.beauties_beast.phonebuddy;

import android.content.Context;

/**
 * Created by boggs on 10/9/15.
 */
public interface CommandInterface {
    boolean parse(String[] sms, Context context);
    boolean action(Object options);
    boolean response(Object options);
}
