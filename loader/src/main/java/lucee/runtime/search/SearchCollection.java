/**
 * Copyright (c) 2014, the Railo Company Ltd.
 * Copyright (c) 2015, Lucee Assosication Switzerland
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
 */
package lucee.runtime.search;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

import lucee.commons.io.log.Log;
import lucee.commons.io.res.Resource;
import lucee.runtime.PageContext;
import lucee.runtime.exp.PageException;
import lucee.runtime.type.Query;
import lucee.runtime.type.QueryColumn;
import lucee.runtime.type.dt.DateTime;

/**
 * a Search Collection
 */
public interface SearchCollection extends Serializable {

	/**
	 * Field <code>SEARCH_TYPE_SIMPLE</code>
	 */
	public static final short SEARCH_TYPE_SIMPLE = 0;

	/**
	 * Field <code>SEARCH_TYPE_EXPLICIT</code>
	 */
	public static final short SEARCH_TYPE_EXPLICIT = 1;

	/**
	 * create a collection
	 * 
	 * @throws SearchException Search Exception
	 */
	public abstract void create() throws SearchException;

	/**
	 * optimize a Collection
	 * 
	 * @throws SearchException Search Exception
	 */
	public abstract void optimize() throws SearchException;

	/**
	 * map a Collection
	 * 
	 * @param path path
	 * @throws SearchException Search Exception
	 */
	public abstract void map(Resource path) throws SearchException;

	/**
	 * repair a Collection
	 * 
	 * @throws SearchException Search Exception
	 */
	public abstract void repair() throws SearchException;

	/**
	 * updates an index of a collection
	 * 
	 * @param pc Page Context
	 * @param key Key
	 * @param type Type
	 * @param urlpath Query Name
	 * @param title title
	 * @param body body
	 * @param language language
	 * @param extensions extensions
	 * @param query query
	 * @param recurse recure
	 * @param categoryTree category tree
	 * @param categories categories
	 * @param timeout timeout
	 * @param custom1 custom1
	 * @param custom2 custom2
	 * @param custom3 custom3
	 * @param custom4 custom4
	 * @return Index Result
	 * @throws PageException Page Exception
	 * @throws MalformedURLException Malformed URL Exception
	 * @throws SearchException Search Exception
	 */
	public abstract IndexResult index(PageContext pc, String key, short type, String urlpath, String title, String body, String language, String[] extensions, String query,
			boolean recurse, String categoryTree, String[] categories, long timeout, String custom1, String custom2, String custom3, String custom4)
			throws PageException, MalformedURLException, SearchException;

	/**
	 * updates a collection with a file
	 * 
	 * @param id id
	 * @param title Title
	 * @param file file
	 * @param language language
	 * @return Index Result
	 * @throws SearchException Search Exception
	 */
	public abstract IndexResult indexFile(String id, String title, Resource file, String language) throws SearchException;

	/**
	 * updates a collection with a path
	 * 
	 * @param id id
	 * @param title Title
	 * @param dir Directory
	 * @param recurse recurse
	 * @param extensions extensions
	 * @param language language
	 * @return Index Result
	 * @throws SearchException Search Exception
	 */
	public abstract IndexResult indexPath(String id, String title, Resource dir, String[] extensions, boolean recurse, String language) throws SearchException;

	/**
	 * updates a collection with an url
	 * 
	 * @param id id
	 * @param title Title
	 * @param recurse Recurse
	 * @param extensions extensions
	 * @param url url
	 * @param language language
	 * @param timeout timeout
	 * @return Index Result
	 * @throws SearchException Search Exception
	 */
	public abstract IndexResult indexURL(String id, String title, URL url, String[] extensions, boolean recurse, String language, long timeout) throws SearchException;

	/**
	 * updates a collection with a custom
	 * 
	 * @param id id
	 * @param title Title for the Index
	 * @param keyColumn Key Column
	 * @param bodyColumns Body Column Array
	 * @param language Language for index
	 * @param custom1 custom1
	 * @param custom2 custom2
	 * @param custom3 custom3
	 * @param custom4 custom4
	 * @return Index Result
	 * @throws SearchException Search Exception
	 * 
	 */
	public abstract IndexResult indexCustom(String id, QueryColumn title, QueryColumn keyColumn, QueryColumn[] bodyColumns, String language, QueryColumn custom1,
			QueryColumn custom2, QueryColumn custom3, QueryColumn custom4) throws SearchException;

	/**
	 * updates a collection with a custom
	 * 
	 * @param id id
	 * @param title Title for the Index
	 * @param keyColumn Key Column
	 * @param bodyColumns Body Column Array
	 * @param language Language for index
	 * @param urlpath Url Path
	 * @param custom1 custom1
	 * @param custom2 custom2
	 * @param custom3 custom3
	 * @param custom4 custom4
	 * @throws SearchException FUTURE add public abstract IndexResult indexCustom(String id, QueryColumn
	 *             title, QueryColumn keyColumn, QueryColumn[] bodyColumns, String language,QueryColumn
	 *             urlpath, QueryColumn custom1, QueryColumn custom2, QueryColumn custom3,QueryColumn
	 *             custom4) throws SearchException;
	 */

	/**
	 * @return Returns the language.
	 */
	public abstract String getLanguage();

	/**
	 * purge a collection
	 * 
	 * @return Index Result
	 * @throws SearchException Search Exception
	 */
	public abstract IndexResult purge() throws SearchException;

	/**
	 * delete the collection
	 * 
	 * @return Index Result
	 * @throws SearchException Search Exception
	 */
	public abstract IndexResult delete() throws SearchException;

	/**
	 * delete an Index from collection
	 * 
	 * @param pc Page Context
	 * @param key Key
	 * @param type Type
	 * @param queryName Query Name
	 * @return Index Result
	 * @throws SearchException Search Exception
	 */
	public abstract IndexResult deleteIndex(PageContext pc, String key, short type, String queryName) throws SearchException;

	/**
	 * @return Returns the path.
	 */
	public abstract Resource getPath();

	/**
	 * @return returns when collection is created
	 */
	public abstract DateTime getCreated();

	/**
	 * @return Returns the lastUpdate.
	 */
	public abstract DateTime getLastUpdate();

	/**
	 * @return Returns the name.
	 */
	public abstract String getName();

	/**
	 * @return Returns the logFile.
	 */
	public abstract Log getLogger();

	/**
	 * @return Returns the searchEngine.
	 */
	public abstract SearchEngine getSearchEngine();

	/**
	 * return time when collection was created
	 * 
	 * @return create time
	 */
	public abstract Object created();

	/**
	 * search the collection
	 * 
	 * @param data data
	 * @param qry Query to append results
	 * @param criteria Search Criteria
	 * @param language Language
	 * @param type SEARCH_TYPE_EXPLICIT or SEARCH_TYPE_SIMPLE
	 * @param startrow start row
	 * @param maxrow max rows
	 * @param categoryTree catgeory Tree
	 * @param category catgeory
	 * @return new startrow
	 * @throws SearchException Search Exception
	 * @throws PageException Page Exception
	 */
	public abstract int search(SearchData data, Query qry, String criteria, String language, short type, int startrow, int maxrow, String categoryTree, String[] category)
			throws SearchException, PageException;

	/**
	 * search the collection
	 * 
	 * @param data data
	 * @param criteria Search Criteria
	 * @param language Language
	 * @param type SEARCH_TYPE_EXPLICIT or SEARCH_TYPE_SIMPLE
	 * @param categoryTree catgeory Tree
	 * @param category catgeory
	 * @return Result as SearchRecord Array
	 * @throws SearchException Search Exception
	 */
	public abstract SearchResulItem[] _search(SearchData data, String criteria, String language, short type, String categoryTree, String[] category) throws SearchException;

	/**
	 * @return the size of the collection in KB
	 */
	public long getSize();

	/**
	 * @return the counts of the documents in the collection
	 */
	public int getDocumentCount();

	public int getDocumentCount(String id);

	public abstract Object getCategoryInfo();

	public Query getIndexesAsQuery();

	void addIndex(SearchIndex si);

}