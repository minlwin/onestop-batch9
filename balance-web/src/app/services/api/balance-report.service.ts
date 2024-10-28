import { Injectable } from "@angular/core";
import { environment } from "../../../environments/environment";
import { HttpClient } from "@angular/common/http";
import { PageInfo } from ".";

const URL = `${environment.baseUrl}/report`

@Injectable({providedIn: 'root'})
export class BalanceReportService {

  constructor(private http:HttpClient) {}

  search(form:any) {
    return this.http.get<PageInfo>(URL, {params: form})
  }
}
