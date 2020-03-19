/*
 * Decompiled with CFR 0.145.
 */
package pat;

import java.io.File;
import javax.swing.filechooser.FileFilter;

class MyCustomFilter1
extends FileFilter {
    MyCustomFilter1() {
    }

    @Override
    public boolean accept(File file) {
        return file.isDirectory() || file.getAbsolutePath().endsWith(".txt");
    }

    @Override
    public String getDescription() {
        return "Text file (*.txt) Do not add extension in filename";
    }
}

