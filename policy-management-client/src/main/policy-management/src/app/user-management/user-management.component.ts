import { Component, OnInit } from '@angular/core';
import { IUser } from '../model/usermodel';
import { UserService } from '../_services/user.service';
import { ToastrService } from 'ngx-toastr';


@Component({
  selector: 'app-user-management',
  templateUrl: './user-management.component.html',
  styleUrls: ['./user-management.component.css']
})
export class UserManagementComponent implements OnInit {

  config: any;
  collection = { count: 6, data: [] as any };

  users: IUser[] = [];
  searchTerm: string ='';
  click : boolean = false;

  roleList = [];
  statusType: string[] = ["ACTIVE", "INACTIVE"];
  dropdownSettings = {};

  constructor(
    private userService: UserService,
    private toastr: ToastrService,
  ) {
    this.config = {
      itemsPerPage: 2,
      currentPage: 1,
      totalItems: this.users.length
    };
  }

  ngOnInit(): void {
    this.userService.getAlluser().subscribe(
      (data) => {

        this.users = JSON.parse(data);
        console.log(this.users)
      },
      (err) => {
        console.log(err)
      }
    );


    this.userService.getAllrole().subscribe(
      (data) => {

        this.roleList = JSON.parse(data);
      },
      (err) => {
        console.log(err)
      }
    );

    this.dropdownSettings = {
      singleSelection: false,
      idField: 'roleId',
      textField: 'roleName',
      selectAllText: 'Select All',
      unSelectAllText: 'UnSelect All',
      itemsShowLimit: 3,
      allowSearchFilter: true
    };
    
  }


  onItemSelect(item: any) {
    this.click = true;
    console.log(item);
  }
  
  onchange(item: any) {
    this.click = true;
    console.log(item);
  }

  pageChanged(event: any) {
    this.config.currentPage = event;
  }

  onRegister(user :IUser){
    console.log(user);

    this.userService.updateUser(user).subscribe(
      (data) => {
      console.log(JSON.stringify(data));
      this.toastr.success(`Role and Status updated successfully for username : ${user.username} !`,'Sucessfully Updated');

      },
      (err) => {
        console.log(err)
        this.toastr.error(`Please check server log for  details !`,'Something went wrong!');
      }
    );
  }
}
