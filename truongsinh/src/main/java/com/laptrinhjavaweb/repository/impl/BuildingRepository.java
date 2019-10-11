package com.laptrinhjavaweb.repository.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.laptrinhjavaweb.buildersearch.BuildingSearchBuilder;
import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.page.PageModel;
import com.laptrinhjavaweb.repository.IBuildingRepository;

public class BuildingRepository extends SimpleJpaRepository<BuildingEntity> implements IBuildingRepository {
	@Override
	public List<BuildingEntity> findAll(HashMap<String, Object> map, PageModel page,
			BuildingSearchBuilder searchBuilder) {
		return findAll(customSQL(map,searchBuilder), page);
	}

	@Override
	public List<BuildingEntity> findAll(HashMap<String, Object> map, BuildingSearchBuilder searchBuilder) {
		return findAll(customSQL(map,searchBuilder));
	}
	private String customSQL(HashMap<String, Object> map,BuildingSearchBuilder searchBuilder)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("select * from building A");
		if (searchBuilder.getStaffid() != null)
			sql.append(" INNER JOIN assignmentstaff s on s.buildingid = A.id");
		sql.append(" where 1 = 1");
		if(searchBuilder.getStaffid() != null)
		{
			sql.append(" and s.staffid = " + searchBuilder.getStaffid());
		}
		sql.append(createSQL(map));
		sql.append(specialSql(searchBuilder));
		return sql.toString();
	}
	public String specialSql(BuildingSearchBuilder searchBuilder) {
		StringBuilder sql = new StringBuilder();
		if (searchBuilder.getCostRentfrom() != null || searchBuilder.getCostRentTo() != null) {
			if (searchBuilder.getCostRentfrom() != null)
				sql.append(" and costrent >= " + searchBuilder.getCostRentfrom());
			if (searchBuilder.getCostRentTo() != null)
				sql.append(" and costrent <= " + searchBuilder.getCostRentTo());
		}
		// and (type like '%TANG_TRET%' or type like '%NGUYEN_CAN%')
		if (searchBuilder.getBuilddingType().length > 0) {
			if(StringUtils.isNotBlank(searchBuilder.getBuilddingType()[0]))
				sql.append(" and (type like '%" + searchBuilder.getBuilddingType()[0] + "%'");
			String type[] = searchBuilder.getBuilddingType();
			for (int i = 1; i < type.length; i++) {
				if(StringUtils.isNotBlank(type[i]))
					sql.append(" or type like '%" + type[i] + "%'");
			}
			

		}
		if (searchBuilder.getRentAreaFrom() != null || searchBuilder.getRentAreaTo() != null) {
			sql.append(" and EXISTS (SELECT * FROM rentarea ra WHERE (ra.buildingid = A.id");
			if (searchBuilder.getRentAreaFrom() != null)
				sql.append(" and ra.value >= " + searchBuilder.getRentAreaFrom());
			if (searchBuilder.getRentAreaTo() != null)
				sql.append(" and ra.value <= " + searchBuilder.getRentAreaTo());
			sql.append("))");
		}
		return sql.toString();
	}

}
