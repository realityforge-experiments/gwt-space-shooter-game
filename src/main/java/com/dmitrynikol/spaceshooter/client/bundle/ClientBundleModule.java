package com.dmitrynikol.spaceshooter.client.bundle;

import com.google.gwt.inject.client.AbstractGinModule;

/**
 * @author Dmitry Nikolaenko
 */
public class ClientBundleModule
  extends AbstractGinModule
{
  @Override
  protected void configure()
  {
    bind( SpaceShooterClientBundle.class ).to( GeneralClientBundle.class );
  }
}

