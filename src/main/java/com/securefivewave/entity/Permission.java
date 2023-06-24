package com.securefivewave.entity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
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
public class Permission {
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty(message = "Role ID cannot be empty")
	@Column(name = "role_id")
	private Long roleId;
	@NotEmpty(message = "Object ID cannot be empty")
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
}
