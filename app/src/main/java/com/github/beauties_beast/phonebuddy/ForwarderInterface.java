package com.github.beauties_beast.phonebuddy;

import android.content.Context;

/**
 * Created by boggs on 10/10/15.
 */
public interface ForwarderInterface {
    boolean parse(Object object, Context context);
    boolean action(Object options, Context context);
    boolean response(Object options, Context context);
}
