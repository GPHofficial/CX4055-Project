/*
 * Decompiled with CFR 0.145.
 */
package pat;

import java.io.File;
import javax.swing.filechooser.FileFilter;

class MyCustomFilter
extends FileFilter {
    MyCustomFilter() {
    }

    @Override
    public boolean accept(File file) {
        return file.isDirectory() || file.getAbsolutePath().endsWith(".csv");
    }

    @Override
    public String getDescription() {
        return "Trace Files (*.csv)";
    }
}

