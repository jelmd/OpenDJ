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
 *      Copyright 2010 Sun Microsystems, Inc.
 *      Portions copyright 2011-2012 ForgeRock AS.
 */

package org.forgerock.opendj.ldap;

import com.forgerock.opendj.util.CompletedFutureResult;
import com.forgerock.opendj.util.Validator;

/**
 * A load balancing connection factory allocates connections using the provided
 * algorithm.
 */
final class LoadBalancer implements ConnectionFactory {
    private final LoadBalancingAlgorithm algorithm;

    /**
     * Creates a new load balancer using the provided algorithm.
     *
     * @param algorithm
     *            The load balancing algorithm which will be used to obtain the
     *            next connection factory.
     */
    public LoadBalancer(final LoadBalancingAlgorithm algorithm) {
        Validator.ensureNotNull(algorithm);
        this.algorithm = algorithm;
    }

    /**
     * {@inheritDoc}
     */
    public Connection getConnection() throws ErrorResultException {
        return algorithm.getConnectionFactory().getConnection();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FutureResult<Connection> getConnectionAsync(
            final ResultHandler<? super Connection> resultHandler) {
        final ConnectionFactory factory;

        try {
            factory = algorithm.getConnectionFactory();
        } catch (final ErrorResultException e) {
            if (resultHandler != null) {
                resultHandler.handleErrorResult(e);
            }
            return new CompletedFutureResult<Connection>(e);
        }

        return factory.getConnectionAsync(resultHandler);
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("LoadBalancer(");
        builder.append(String.valueOf(algorithm));
        builder.append(')');
        return builder.toString();
    }
}
