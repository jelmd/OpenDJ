/*
 * CDDL HEADER START
 *
 * The contents of this file are subject to the terms of the
 * Common Development and Distribution License, Version 1.0 only
 * (the "License").  You may not use this file except in compliance
 * with the License.
 *
 * You can obtain a copy of the license at legal-notices/CDDLv1_0.txt
 * or http://forgerock.org/license/CDDLv1.0.html.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL HEADER in each
 * file and include the License file at legal-notices/CDDLv1_0.txt.
 * If applicable, add the following below this CDDL HEADER, with the
 * fields enclosed by brackets "[]" replaced with your own identifying
 * information:
 *      Portions Copyright [yyyy] [name of copyright owner]
 *
 * CDDL HEADER END
 *
 *
 *      Copyright 2008 Sun Microsystems, Inc.
 *      Portions Copyright 2013-2015 ForgeRock AS.
 */
package org.opends.server.extensions;



import java.util.ArrayList;
import org.opends.server.TestCaseUtils;
import org.opends.server.admin.server.AdminTestCaseUtils;
import org.testng.annotations.BeforeClass;
import org.opends.server.admin.std.meta.*;
import org.opends.server.admin.std.server.SoftReferenceEntryCacheCfg;
import org.opends.server.types.Entry;
import org.opends.server.util.ServerConstants;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;
import static org.testng.Assert.*;



/**
 * A set of test cases for SoftReference entry cache implementation.
 */
@Test(groups = "entrycache", sequential=true)
public class SoftReferenceEntryCacheTestCase
       extends CommonEntryCacheTestCase<SoftReferenceEntryCacheCfg>
{
  /**
   * Initialize the entry cache test.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @BeforeClass
  public void entryCacheTestInit()
         throws Exception
  {
    // Ensure that the server is running.
    TestCaseUtils.startServer();

    // Configure this entry cache.
    Entry cacheConfigEntry = TestCaseUtils.makeEntry(
      "dn: cn=Soft Reference,cn=Entry Caches,cn=config",
      "objectClass: ds-cfg-soft-reference-entry-cache",
      "objectClass: ds-cfg-entry-cache",
      "objectClass: top",
      "cn: Soft Reference",
      "ds-cfg-cache-level: 1",
      "ds-cfg-java-class: " +
      "org.opends.server.extensions.SoftReferenceEntryCache",
      "ds-cfg-enabled: true");
    super.configuration = AdminTestCaseUtils.getConfiguration(
      SoftReferenceEntryCacheCfgDefn.getInstance(), cacheConfigEntry);

    // Force GC to make sure we have enough memory for
    // the cache capping constraints to work properly.
    System.gc();

    // Initialize the cache.
    super.cache = new SoftReferenceEntryCache();
    super.cache.initializeEntryCache(configuration);

    // Make some dummy test entries.
    super.testEntriesList = new ArrayList<>(super.NUMTESTENTRIES);
    for(int i = 0; i < super.NUMTESTENTRIES; i++ ) {
      super.testEntriesList.add(TestCaseUtils.makeEntry(
        "dn: uid=test" + i + ".user" + i + ",ou=test" + i + ",o=test",
        "objectClass: person",
        "objectClass: inetorgperson",
        "objectClass: top",
        "objectClass: organizationalperson",
        "postalAddress: somewhere in Testville" + i,
        "street: Under Construction Street" + i,
        "l: Testcounty" + i,
        "st: Teststate" + i,
        "telephoneNumber: +878 8378 8378" + i,
        "mobile: +878 8378 8378" + i,
        "homePhone: +878 8378 8378" + i,
        "pager: +878 8378 8378" + i,
        "mail: test" + i + ".user" + i + "@testdomain.net",
        "postalCode: 8378" + i,
        "userPassword: testpassword" + i,
        "description: description for Test" + i + "User" + i,
        "cn: Test" + i + "User" + i,
        "sn: User" + i,
        "givenName: Test" + i,
        "initials: TST" + i,
        "employeeNumber: 8378" + i,
        "uid: test" + i + ".user" + i)
      );
    }
  }



  /**
   * Finalize the entry cache test.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @AfterClass
  public void entryCacheTestFini()
         throws Exception
  {
    super.cache.finalizeEntryCache();
  }



  /** {@inheritDoc} */
  @Test
  @Override
  public void testContainsEntry()
         throws Exception
  {
    super.testContainsEntry();
  }



  /** {@inheritDoc} */
  @Test
  @Override
  public void testGetEntry1()
         throws Exception
  {
    super.testGetEntry1();
  }



  /** {@inheritDoc} */
  @Test
  @Override
  public void testGetEntry2()
         throws Exception
  {
    super.testGetEntry2();
  }



  /** {@inheritDoc} */
  @Test
  @Override
  public void testGetEntry3()
         throws Exception
  {
    super.testGetEntry3();
  }



  /** {@inheritDoc} */
  @Test
  @Override
  public void testGetEntryID()
         throws Exception
  {
    super.testGetEntryID();
  }



  /** {@inheritDoc} */
  @Test
  @Override
  public void testPutEntry()
         throws Exception
  {
    super.testPutEntry();
  }



  /** {@inheritDoc} */
  @Test
  @Override
  public void testPutEntryIfAbsent()
         throws Exception
  {
    super.testPutEntryIfAbsent();
  }



  /** {@inheritDoc} */
  @Test
  @Override
  public void testRemoveEntry()
         throws Exception
  {
    super.testRemoveEntry();
  }



  /** {@inheritDoc} */
  @Test
  @Override
  public void testClear()
         throws Exception
  {
    super.testClear();
  }



  /** {@inheritDoc} */
  @Test
  @Override
  public void testClearBackend()
         throws Exception
  {
    super.testClearBackend();
  }



  /** {@inheritDoc} */
  @Test
  @Override
  public void testClearSubtree()
         throws Exception
  {
    super.testClearSubtree();
  }



  /** {@inheritDoc} */
  @Test
  @Override
  public void testHandleLowMemory()
         throws Exception
  {
    super.testHandleLowMemory();
  }



  @BeforeGroups(groups = "testSoftRefCacheConcurrency")
  public void cacheConcurrencySetup()
         throws Exception
  {
    assertNull(cache.toVerboseString(),
      "Expected empty cache.  " + "Cache contents:" + ServerConstants.EOL +
      cache.toVerboseString());
  }



  @AfterGroups(groups = "testSoftRefCacheConcurrency")
  public void cacheConcurrencyCleanup()
         throws Exception
  {
    // Clear the cache so that other tests can start from scratch.
    super.cache.clear();
  }



  /** {@inheritDoc} */
  @Test(groups = { "slow", "testSoftRefCacheConcurrency" },
        threadPoolSize = 10,
        invocationCount = 10,
        timeOut = 60000)
  @Override
  public void testCacheConcurrency()
         throws Exception
  {
    super.testCacheConcurrency();
  }
}
