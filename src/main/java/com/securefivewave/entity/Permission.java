package com.securefivewave.entity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = Include.NON_DEFAULT)
@Data
@Entity
@Table (name ="TBL_PERMISSION")
public class Permission {
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "Role Id cannot be blank")
	@Column(name = "role_id")
	private Long roleId;
	@NotBlank(message = "Object ID cannot be blank")
	@Column(name = "obj_id")
	private Long objectId;
	@Column(name = "can_view")
	private Boolean canView;
	@Column(name = "can_add")
	private Boolean canAdd;
	@Column(name = "can_update")
	private Boolean canUpdate;
	@Column(name = "can_delete")
	private Boolean canDelete;
	@Column(name = "can_all")
	private Boolean canAll;
}
