package net.streets.persistence.entity.enumeration;

import net.streets.persistence.entity.super_class.str_entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/***************************************************************************
 *                                                                         *
 * Created:     11 / 05 / 2019                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

@Entity
//@Table(indexes = {@Index(name = "index_system_id",  columnList="system_id", unique = true)})
public class str_response_mapping extends str_entity<str_response_mapping> {

    @Column(nullable = false)
    private String system_id;

    @Column(nullable = false)
    private Long response_code_id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private str_response_code mapped_response_code;

    @Column(nullable = false)
    private String mapped_response_message;

    public str_response_mapping() {}

    public str_response_mapping(String system_id, Long response_code_id, str_response_code mapped_response_code, String mapped_response_message) {
        this.system_id = system_id;
        this.response_code_id = response_code_id;
        this.mapped_response_code = mapped_response_code;
        this.mapped_response_message = mapped_response_message;
    }

    public String getSystem_id() { return system_id; }

    public void setSystem_id(String system_id) { this.system_id = system_id; }

    public Long getResponse_code_id() { return response_code_id; }

    public void setResponse_code_id(Long response_code_id) { this.response_code_id = response_code_id; }

    public str_response_code getMapped_response_code() { return mapped_response_code; }

    public void setMapped_response_code(str_response_code mapped_response_code) { this.mapped_response_code = mapped_response_code; }

    public String getMapped_response_message() { return mapped_response_message; }

    public void setMapped_response_message(String mapped_response_message) { this.mapped_response_message = mapped_response_message; }
}
