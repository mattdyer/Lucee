/**
 *
 * Copyright (c) 2014, the Railo Company Ltd. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either 
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public 
 * License along with this library.  If not, see <http://www.gnu.org/licenses/>.
 * 
 **/
package lucee.commons.collection;

import java.util.Map;

import lucee.commons.collection.concurrent.ConcurrentHashMapPro;

public class MapFactory {
	public static <K, V> MapPro<K, V> getConcurrentMap() {
		// return new HashMapPro();
		return new ConcurrentHashMapPro<K, V>();
	}

	public static <K, V> MapPro<K, V> getConcurrentMap(int initialCapacity) {
		// return new HashMapPro();
		return new ConcurrentHashMapPro<K, V>(initialCapacity);
	}

	public static <K, V> MapPro<K, V> getConcurrentMap(Map<K, V> map) {
		// return new HashMapPro(map);
		return new ConcurrentHashMapPro<K, V>(map);
	}
}