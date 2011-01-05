/*
 * JBoss, Home of Professional Open Source
 *
 * Copyright 2010, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a full listing
 * of individual contributors.
 *
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU General Public License, v. 2.0.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License,
 * v. 2.0 along with this distribution; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, USA.
 */
package net.java.slee.resource.diameter.gx;

import java.io.IOException;

import net.java.slee.resource.diameter.base.events.ReAuthRequest;
import net.java.slee.resource.diameter.gx.events.GxCreditControlAnswer;

/**
 * An GxServerSessionActivity represents a policy control session.
 *
 * A single GxServerSessionActivity will be created for the Diameter session.
 * All requests received for the session will be fired as events on the same
 * GxServerSessionActivity.
 *
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:carl-magnus.bjorkell@emblacom.com"> Carl-Magnus Björkell </a>
 */
public interface GxServerSessionActivity extends GxSessionActivity {

    /**
     * Create a Ro-specific Credit-Control-Answer message pre-populated with the
     * AVPs appropriate for this session.
     *
     * @return a new CreditControlAnswer
     */
    GxCreditControlAnswer createGxCreditControlAnswer();

    /**
     * Sends a CCA message to the client.
     *
     * @param cca the CreditControlAnswer to send
     * @throws IOException if an error occurred while sending the request to the peer
     */
    void sendGxCreditControlAnswer(GxCreditControlAnswer cca) throws IOException;

    /**
     * Sends a re-auth request to the client.
     * @param rar the ReAuthRequest to send.
     * @throws IOException if an error occurred while sending the message to the client.
     */
    void sendReAuthRequest(ReAuthRequest rar) throws IOException;
}