// Copyright The Tuweni Authors
// SPDX-License-Identifier: Apache-2.0
package io.consensys.protocols.util.discovery

import io.vertx.core.Vertx
import io.vertx.kotlin.coroutines.setPeriodicAwait
import org.apache.tuweni.devp2p.EthereumNodeRecord
import org.slf4j.LoggerFactory

/**
 * Resolves DNS records over time, refreshing records.
 *
 * @param enrLink the ENR link to start with, of the form enrtree://PUBKEY@domain
 * @param listener Listener notified when records are read and whenever they are updated.
 * @param dnsServer the DNS server to use for DNS query. If null, the default DNS server will be used.
 * @param seq the sequence number of the root record. If the root record seq is higher, proceed with visit.
 * @param period the period at which to poll DNS records
 * @param resolver
 */
class DNSDaemon @JvmOverloads constructor(
  private val enrLink: String,
  private val listener: DNSDaemonListener?,
  private val seq: Long = 0,
  private val period: Long = 60000L,
  private val dnsServer: String? = null,
  private val vertx: Vertx,
) {
  companion object {
    val logger = LoggerFactory.getLogger(DNSDaemon::class.java)
  }

  private var periodicHandle: Long = 0

  val listeners = HashSet<DNSDaemonListener>()

  init {
    listener?.let {
      listeners.add(listener)
    }
  }

  private fun updateRecords(records: List<EthereumNodeRecord>) {
    listeners.forEach { it.newRecords(seq, records) }
  }

  fun start() {
    logger.trace("Starting DNSDaemon for $enrLink")
    val task = DNSTimerTask(vertx, seq, enrLink, this::updateRecords)
    vertx.setPeriodicAwait(period, task::run)
  }

  /**
   * Close the daemon.
   */
  public fun close() {
    vertx.cancelTimer(this.periodicHandle)
  }
}

internal class DNSTimerTask(
  private val vertx: Vertx,
  private val seq: Long,
  private val enrLink: String,
  private val records: (List<EthereumNodeRecord>) -> Unit,
  private val dnsServer: String? = null,
  private val dnsResolver: DNSResolver = DNSResolver(dnsServer, seq, vertx),
) {

  companion object {
    val logger = LoggerFactory.getLogger(DNSTimerTask::class.java)
  }

  suspend fun run(@Suppress("UNUSED_PARAMETER") timerId: Long) {
    logger.debug("Refreshing DNS records with $enrLink")
    records(dnsResolver.collectAll(enrLink))
  }
}
