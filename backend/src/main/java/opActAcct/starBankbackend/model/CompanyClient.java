package opActAcct.starBankbackend.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CompanyClient extends Client{

    private String company_name;
    private String cluster;

    public CompanyClient(String client_id, ArrayList accounts, String phone, String client_name, String client_occupation, String client_address, String company_name, String cluster) {
        super(client_id, accounts, phone, client_name, client_occupation, client_address);
        this.company_name = company_name;
        this.cluster = cluster;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }
}
