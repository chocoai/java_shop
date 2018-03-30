package com.baigu.app.base.core.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.baigu.eop.sdk.utils.StaticResourcesUtil;
import org.springframework.jdbc.core.RowMapper;

/**
 * 友情连接Mapper
 * @author kingapex
 * 2010-7-17上午11:28:05
 */
public class FriendsLinkMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		FriendsLink friendsLink = new FriendsLink();
		friendsLink.setLink_id(rs.getInt("link_id"));
		String logo  = rs.getString("logo");
		if(logo!=null) logo  = StaticResourcesUtil.convertToUrl(logo);
		friendsLink.setLogo(logo);
		friendsLink.setName(rs.getString("name"));
		friendsLink.setSort(rs.getInt("sort"));
		friendsLink.setUrl(rs.getString("url"));
		return friendsLink;
	}

}
