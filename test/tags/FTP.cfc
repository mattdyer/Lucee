<!--- 
 *
 * Copyright (c) 2016, Lucee Assosication Switzerland. All rights reserved.*
 * Copyright (c) 2014, the Railo Company LLC. All rights reserved.
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
 ---><cfscript>
component extends="org.lucee.cfml.test.LuceeTestCase" labels="ftp" {

	public void function testSFTP() {
		var sftp=getSFTPCredentials();
		if (!structCount(sftp)) return;
		_test(
			secure: true,
			host: sftp.server,
			user: sftp.username,
			pass: sftp.password,
			port: sftp.port,
			base: sftp.base_path
		);
	}
	
	public void function testFTPS() {
		var ftps=getFTPSCredentials();
		if (!structCount(ftps)) return;
		_test(
			secure: true,
			host: ftps.server,
			user: ftps.username,
			pass: ftps.password,
			port: ftps.port,
			base: ftps.base_path
		);
	}

	public void function testFTP() {
		var ftp=getFTPCredentials();
		if (!structCount(ftp)) return;
		_test(
			secure: false,
			host: ftp.server,
			user: ftp.username,
			pass: ftp.password,
			port: ftp.port,
			base: ftp.base_path
		);
	}
	
	private function _test(required boolean secure,required string host,required number port=21,required string user,required string pass,required string base){

		ftp action = "open" 
			connection = "conn" 
			secure=secure
			username = user 
			password = pass 
			server = host
			port=port;


		var folderName="folder"&getTickCount();
		if(right(base,1)!="/")base=base&"/";
		var dir=base&folderName&"/";
		var fileName="test.txt";
		var file=dir&fileName;

		var fileName2="test2.txt";
		var file2=dir&fileName2;

		var subdir=dir&"sub/";
		var subfile=subdir&fileName;
		
		// list the inital state
		ftp action="listdir" directory=base connection = "conn" name="local.list1";
		
		// print working directory
		ftp action="getcurrentdir" directory=base connection = "conn" result="local.pwd1";
		pwd1=pwd1.returnValue;
		///////// TODO does not work with sftp assertTrue(pwd1==base || pwd1&"/"==base);
			
		
		try{

			// we create a directory
			ftp action="createdir" directory=dir connection = "conn";
			ftp action="listdir" directory=base connection = "conn" name="local.list2";
			assertEquals(list1.recordcount+1,list2.recordcount);

			// change working directory
			ftp action="changedir" directory=dir connection = "conn";
			ftp action="getcurrentdir" directory=base connection = "conn" result="local.pwd2";
			pwd2=pwd2.returnValue;
			assertTrue(pwd2==dir || pwd2&"/"==dir);
		
			

			// we add a file
			ftp action="putFile"  localfile=getCurrentTemplatePath() remoteFile=file connection= "conn";
			ftp action="listdir" directory=dir connection = "conn" name="local.list3";
			assertEquals(list3.recordcount,1);
			assertEquals(list3.name,fileName);
			assertEquals(list3.isDirectory,false);
			assertEquals(list3.name,fileName);
			assertEquals(list3.path,file);
			assertEquals(list3.type,"file");

			//systemOutput("SFTP #secure#", true);

			if ( arguments.secure eq true && arguments.secure neq "FTPS" ){ // ftp and sftp are rather different 
				ftp action="quote" actionParams="ls" connection = "conn";
				expect( trim( cfftp.returnValue ) ).NotToBeEmpty();
				//systemOutput(cfftp, true);
			} else {
				ftp action="quote" actionParams="SYST" connection = "conn";
				expect( trim( cfftp.returnValue ) ).NotToBeEmpty();
				//systemOutput(cfftp, true);

					// test action="quote", custom command
				ftp action="quote" actionParams="SIZE #file#" connection = "conn";
				expect( trim( cfftp.returnValue ) ).toBe( "213 " & Len( FileRead( getCurrentTemplatePath( ) ) ) );
				//systemOutput(cfftp, true);

				// test action="quote", custom command, trigger a 550 file not found exception
				ftp action="quote" actionParams="SIZE #file#-missing" connection = "conn";
				expect( trim( cfftp.errorCode ) ).toBe( "550" );
				//systemOutput(cfftp, true);
			}
			
			// we read the file
			var src=getCurrentTemplatePath();
			var localFile=src&"."&getTickcount()&".rf";
			try {
				ftp action="getFile"  localfile=localFile remoteFile=file connection= "conn";
				var srcContent=fileRead(src);
				var localFileContent=fileRead(localFile);
				assertEquals(srcContent,localFileContent);
			}
			finally {
				try {fileDelete(localFile);}catch(local.ee){}
			}

			// we rename the file
			ftp action="rename"  existing=file new=file2 connection= "conn";
			ftp action="listdir" directory=dir connection = "conn" name="local.list4";
			assertEquals(list4.recordcount,1);
			assertEquals(list4.name,fileName2);

			// exists dir
			ftp action="existsdir" directory=dir connection = "conn" result="local.exist1";
			assertTrue(exist1.returnValue);
			ftp action="existsdir" directory=subdir connection = "conn" result="local.exist2";
			assertFalse(exist2.returnValue);

			//exists file
			ftp action="existsfile" remotefile=file2 connection = "conn" result="local.exist3";
			assertTrue(exist3.returnValue);
			ftp action="existsfile" remotefile=file connection = "conn" result="local.exist4";
			assertFalse(exist4.returnValue);


			// we delete the file again
			ftp action="remove"  item=file2 connection= "conn";
			ftp action="listdir" directory=dir connection = "conn" name="local.list4";
			assertEquals(list4.recordcount,0);

			// we add again a file and directory to be sure we can delete a folder with content
			ftp action="createdir" directory=subdir connection = "conn";
			ftp action="putFile"  localfile=getCurrentTemplatePath() remoteFile=subfile connection= "conn";

		}
		finally {
			// delete the folder we did for testing
			ftp action="removedir" directory=dir connection = "conn" recurse=true;
			ftp action="listdir" directory=base connection = "conn" name="local.list20";
			assertEquals(list1.recordcount,list20.recordcount);
		}
	}

	private struct function getFTPCredentials() {
		return creds = server.getTestService("ftp");
	}

	private struct function getFTPSCredentials() {
		return creds = server.getTestService("ftps");
	}

	private struct function getSFTPCredentials() {
		return creds = server.getTestService("sftp");
	}
} 
</cfscript>