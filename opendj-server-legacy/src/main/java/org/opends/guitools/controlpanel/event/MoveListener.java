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
 *      Portions Copyright 2015 ForgeRock AS
 */

package org.opends.guitools.controlpanel.event;

/**
 * Interface that must be implemented by the objects that want to receive
 * notifications when an entry is being moved (for instance using drag and
 * drop).
 * For the time being it is not used but it can be used in the future when the
 * move of the entries is implemented.
 *
 */
public interface MoveListener
{
  /**
   * Notification that the entry is being moved.
   * @param ev the event.
   */
  void processMoveEvent(MoveEvent ev);
}
