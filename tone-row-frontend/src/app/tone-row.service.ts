import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { ToneRow } from './models/ToneRow';

@Injectable({
  providedIn: 'root'
})
export class ToneRowService {

  baseURL: string = "http://localhost:8080/api";
  httpProperties: object = {
    headers: new HttpHeaders({
      "Content-Type": "application/json"
    })
  }

  constructor(private http: HttpClient) { }

  getToneRow(toneRowId: number): Observable<ToneRow | null> {
    return this.http.get<ToneRow>(this.baseURL + `/tonerow?id=${toneRowId}`, this.httpProperties)
    .pipe(
      tap(x => console.log(x)),
      catchError(err => {
        console.log(err);
        return of(null);
      })
    )
  }
}
