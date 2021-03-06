/*
 * Copyright (C) 2012  Krawler Information Systems Pvt Ltd
 * All rights reserved.
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

package com.krawler.hql.accounting;

import com.krawler.common.admin.Company;
import com.krawler.common.admin.CostCenter;
import com.krawler.common.admin.KWLCurrency;
import java.util.Date;
import java.util.Set;

/**
 *
 * @author krawler-user
 */
public class JournalEntry {
    private String ID;
    private String entryNumber;
    private boolean autoGenerated;
    private Date entryDate;
    private String memo;
    private Company company;
    private KWLCurrency currency;
    private Set<JournalEntryDetail> details;
    private double externalCurrencyRate;
    private boolean deleted;
    private CostCenter costcenter;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getEntryNumber() {
        return entryNumber;
    }

    public void setEntryNumber(String entryNumber) {
        this.entryNumber = entryNumber;
    }

    public boolean isAutoGenerated() {
        return autoGenerated;
    }

    public void setAutoGenerated(boolean autoGenerated) {
        this.autoGenerated = autoGenerated;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Set<JournalEntryDetail> getDetails() {
        return details;
    }

    public void setDetails(Set<JournalEntryDetail> details) {
        this.details = details;
    }

    public KWLCurrency getCurrency() {
        return currency;
    }

    public void setCurrency(KWLCurrency currency) {
        this.currency = currency;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public double getExternalCurrencyRate() {
        return externalCurrencyRate;
    }

    public void setExternalCurrencyRate(double externalCurrencyRate) {
        this.externalCurrencyRate = externalCurrencyRate;
    }

    public CostCenter getCostcenter() {
        return costcenter;
    }

    public void setCostcenter(CostCenter costcenter) {
        this.costcenter = costcenter;
    }
}
