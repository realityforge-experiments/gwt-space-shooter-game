package com.dmitrynikol.spaceshooter.client.linker;

import com.google.gwt.core.ext.linker.Shardable;

/**
 * 
 * Linker create the cacheble manifest file
 * 
 * @author Dmitry Nikolaenko
 *
 */
@Shardable
public class ApplicationCacheManifestLinker extends PersonalLinker {
	
	@Override
	protected String[] getCacheExtraFiles() {
		return new String[] { "SpaceShooter.html", "SpaceShooter.css" };
	}
	
	@Override
	protected String getCacheManifestTemplate() {
		return "spaceshooter.appcache.manifest";
	}
}
