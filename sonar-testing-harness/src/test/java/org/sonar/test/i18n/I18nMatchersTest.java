/*
 * Sonar, open source software quality management tool.
 * Copyright (C) 2008-2012 SonarSource
 * mailto:contact AT sonarsource DOT com
 *
 * Sonar is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * Sonar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Sonar; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.test.i18n;

import org.junit.Test;

import java.io.File;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.sonar.test.i18n.I18nMatchers.isBundleUpToDate;

public class I18nMatchersTest {

  @Test
  public void testBundlesInsideSonarPlugin() {
    // synchronized bundle
    assertThat("myPlugin_fr_CA.properties", isBundleUpToDate());
    assertFalse(new File("target/l10n/myPlugin_fr_CA.properties.report.txt").exists());
    // missing keys
    try {
      assertThat("myPlugin_fr.properties", isBundleUpToDate());
      assertTrue(new File("target/l10n/myPlugin_fr.properties.report.txt").exists());
    } catch (AssertionError e) {
      assertThat(e.getMessage(), containsString("Missing translations are:\nsecond.prop"));
    }
  }

  @Test
  public void shouldNotFailIfNoMissingKeysButAdditionalKeys() {
    assertThat("noMissingKeys_fr.properties", isBundleUpToDate());
  }
}
