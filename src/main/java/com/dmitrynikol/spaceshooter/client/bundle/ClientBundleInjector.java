package com.dmitrynikol.spaceshooter.client.bundle;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

/**
 * @author Dmitry Nikolaenko
 */
@GinModules(ClientBundleModule.class)
public interface ClientBundleInjector
  extends Ginjector
{
  SpaceShooterClientBundle spaceShooterBundle();
}

