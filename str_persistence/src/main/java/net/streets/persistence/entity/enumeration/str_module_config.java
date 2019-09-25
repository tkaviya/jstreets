package net.streets.persistence.entity.enumeration;

import net.streets.persistence.entity.super_class.str_enum_entity;

import javax.persistence.*;

/***************************************************************************
 *                                                                         *
 * Created:     24 / 09 / 2019                                             *
 * Author:      Tsungai Kaviya                                             *
 * System:      IntelliJ 2019 / Windows 10                                 *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

@Entity
@AttributeOverrides({
		@AttributeOverride(name = "id", column = @Column(name = "module_config_id")),
		@AttributeOverride(name = "name", column = @Column(name = "module_name", unique = true))
})
@Cacheable
public class str_module_config extends str_enum_entity<str_currency> {

	@Basic
	String module_run_times;

	public String getModule_run_times() { return module_run_times; }

	public void setModule_run_times(String module_run_times) { this.module_run_times = module_run_times; }
}
