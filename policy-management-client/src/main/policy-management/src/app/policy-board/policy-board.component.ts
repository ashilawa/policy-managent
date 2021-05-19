import { Component, OnInit ,ViewChild} from '@angular/core';
import { IPolicy } from '../model/policymodel';
import { TokenStorageService } from '../_services/token-storage.service';
import { PolicyService } from '../_services/policy.service';
import { NgbModal ,NgbAlert} from '@ng-bootstrap/ng-bootstrap';
import { FormGroup, FormBuilder } from '@angular/forms';
import {Subject} from 'rxjs';
import {debounceTime} from 'rxjs/operators';

@Component({
  selector: 'app-policy-board',
  templateUrl: './policy-board.component.html',
  styleUrls: ['./policy-board.component.css']
})
export class PolicyBoardComponent implements OnInit {

  
  policies?: IPolicy[];
  errorMessage?: string;
  showAdmin = false;
  policy?: IPolicy;
  editPolicyForm!: FormGroup;
  private _success = new Subject<string>();
  successMessage = '';
  isSuccess = false;
  @ViewChild('selfClosingAlert', {static: false}) selfClosingAlert?: NgbAlert;


  constructor(
    private policyService: PolicyService,
    private tokenStorageService: TokenStorageService,
    private modalService: NgbModal,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    const user = this.tokenStorageService.getUser();

    this.showAdmin = user.roles.includes('ADMIN');

    if(this.showAdmin){
      this.editPolicyForm = this.fb.group({
        policyId: [{ value: '' ,disabled: true}],
        policyName: [{ value: '' }],
        premium: [{ value: '' }],
        sumAssured: [{ value: '' }],
        years: [{ value: '' }],
        policyType: [{ value: '' }],
        description: [{ value: '' }],
      });

    }
    else{
      this.editPolicyForm = this.fb.group({
        policyId: [{ value: '', disabled: true }],
        policyName: [{ value: '', disabled: true }],
        premium: [{ value: '', disabled: true }],
        sumAssured: [{ value: '', disabled: true }],
        years: [{ value: '', disabled: true }],
        policyType: [{ value: '', disabled: true }],
        description: [{ value: '', disabled: true }],
      });
    }

    this.policyService.getAdminBoard().subscribe(
      (data) => {
        this.policies = JSON.parse(data);
      },
      (err) => {
        this.errorMessage = JSON.parse(err.error).message;
      }
    );

    this._success.subscribe(message => this.successMessage = message);
    this._success.pipe(debounceTime(5000)).subscribe(() => {
      if (this.selfClosingAlert) {
        this.selfClosingAlert.close();
      }
    });
  }

  deletePolicy(policy: IPolicy) {
    console.log('delete' + policy.policyId);
    this.policyService.deletePolicy(policy.policyId.toString()).subscribe(
      (data) => {
        console.log('data' + data);
        this.reloadPage();
        
      },
      (err) => {
        this.errorMessage = JSON.parse(err.error).message;
        console.log(err);
      }
    );
  }

  openModal(targetModal: any, policy: IPolicy) {
    this.modalService.open(targetModal, {
    });
    this.policy = policy;
  }

  openEditModal(targetModal: any, policy: IPolicy) {
    this.modalService.open(targetModal, {
    });
    this.editPolicyForm.patchValue({
      policyId: policy.policyId,
      policyName: policy.policyName,
      premium: policy.premium,
      sumAssured: policy.sumAssured,
      years: policy.years,
      policyType: policy.policyType,
      description: policy.description,
    });
  }

  onDelete() {
    if (this.policy) this.deletePolicy(this.policy);
  }

  onSubmit() {
    this.modalService.dismissAll();
    console.log("res:", this.editPolicyForm.getRawValue());

    this.policyService.updatePolicy(this.editPolicyForm.getRawValue()).subscribe(
      (data) => {
        console.log('data' + data);
        this.reloadPage();
      },
      (err) => {
        this.errorMessage = JSON.parse(err.error).message;
        console.log(err);
      }
    );

   }
   onRegister(policy: IPolicy){
    this.policyService.registerUserPolicy(this.tokenStorageService.getUser().username, policy.policyId.toString()).subscribe(
      (data) => {

       let response : any  = JSON.parse(data);
        console.log('data' + response.message);
       // this.reloadPage();
       if(response.message == "R")
       {
        this.isSuccess = true;
        this._success.next(`Policy registered successfully !`);
       }
       else
       {
        this.isSuccess = false;
        this._success.next(`Policy Already exist. Contact to Admin !`);
       }
      },
      (err) => {
        this.errorMessage = JSON.parse(err.error).message;
        console.log(err);
      }
    );

   
   }

  reloadPage(): void {
    window.location.reload();
  }

}
