package com.bumptech.glide.load.resource.file;

import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;

import java.io.File;

/**
 * A simple {@link com.bumptech.glide.load.ResourceDecoder} that creates resource for a given {@link java.io.File}.
 */
public class FileDecoder implements ResourceDecoder<File, File> {

    @Override
    public Resource<File> decode(File source, int width, int height, boolean decodeByOriginalIns) {
        return new FileResource(source);
    }

    @Override
    public String getId() {
        return "";
    }
}
