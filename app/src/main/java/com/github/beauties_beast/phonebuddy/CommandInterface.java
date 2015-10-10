package com.github.beauties_beast.phonebuddy;

import android.content.Context;

/**
 * Created by boggs on 10/9/15.
 */
public interface CommandInterface {
    boolean parse(String sms, Context context);
    boolean action(Object options, Context context);
    boolean response(Object options, Context context);
}
