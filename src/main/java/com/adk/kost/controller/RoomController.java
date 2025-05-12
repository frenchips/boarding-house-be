package com.adk.kost.controller;



import com.adk.kost.dto.RoomDto;
import com.adk.kost.entity.Room;
import com.adk.kost.request.roomRequest.DeleteDetailRoomRequest;
import com.adk.kost.request.roomRequest.RoomRequest;
import com.adk.kost.request.roomRequest.SearchRoomRequest;
import com.adk.kost.response.Response;
import com.adk.kost.response.SearchResponse;
import com.adk.kost.service.RoomServiceImpl;
import com.adk.kost.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
//@RequestMapping("/adk/master")
public class RoomController {

    @Autowired
    RoomServiceImpl roomService;

    @Autowired
    ResponseUtil resp;


//
    @RequestMapping(value = "/addRoom", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Room> addRoom(@RequestBody RoomRequest request){

        Room rm = roomService.create(request);

        return resp.generateResponseSuccess(rm);
    }


    @RequestMapping(value = "/deleteRoom", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response deleteRoom(@RequestBody DeleteDetailRoomRequest request){

         roomService.deleteDetail(request);
         return resp.generateResponseSuccess(null);
    }


    @RequestMapping(value = "/deleteRoom/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Room> delete(@PathVariable String id){

        Room rm = roomService.delete(id);

        return resp.generateResponseSuccess(rm);
    }

    @RequestMapping(value = "/searchRoom", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SearchResponse> searchRoom(@RequestBody SearchRoomRequest request){

        List<RoomDto> listData = roomService.search(request);

        return resp.generateSearchResponse(request, listData);
    }


    @RequestMapping(value = "/getRoom/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Long> getRoom(@PathVariable("id") String no){

        Long id = roomService.byId(no);

        return resp.generateResponseSuccess(id);
    }


    @RequestMapping(value = "/getRoomPrice/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<RoomDto> getRoomPrice(@PathVariable("id") String no){

        RoomDto id = roomService.byRoomAndPrice(no);

        return resp.generateResponseSuccess(id);
    }


    @RequestMapping(value = "/getLoadedRoom/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Room> getLoadedRoom(@PathVariable("id") String no){

        Room id = roomService.getLoaded(no);

        return resp.generateResponseSuccess(id);
    }


}
