package com.example.demoblog.controller;


import com.example.demoblog.model.Comment;
import com.example.demoblog.model.Post;
import com.example.demoblog.repository.CommentRepository;
import com.example.demoblog.repository.PostRepository;
import com.example.demoblog.util.PostGrid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/posts")
public class PostController {

    private final Logger log = LoggerFactory.getLogger(PostController.class);

    @Autowired
    PostRepository postRepository;
    @Autowired
    CommentRepository commentRepository;

/*    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public Post createPost(@RequestBody Post post)
    {
        return postRepository.save(post);
    }*/



    @PostMapping("")
    public ResponseEntity<Post> createPost(@RequestBody @Valid Post post, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(post, HttpStatus.BAD_REQUEST);
        }
        Post savedPost = postRepository.save(post);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("MyResponseHeader1", "MyValue1");
        responseHeaders.set("MyResponseHeader2", "MyValue2");

        return new ResponseEntity<>(savedPost, responseHeaders, HttpStatus.CREATED);
    }

    @GetMapping("")
    public List<Post> listPosts()
    {
        return postRepository.findAll();
    }



    @GetMapping(value="/{id}")
    public Post getPost(@PathVariable("id") Integer id)
    {
        return postRepository.findById(id)
                . orElseThrow(() -> new ResourceNotFoundException
                        ("No post found with id="+id));
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable("id") Integer id, @RequestBody
            Post post)
    {
        postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException
                        ("No post found with id="+id));
        return postRepository.save(post);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable("id") Integer id)
    {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException
                        ("No post found with id="+id));
        postRepository.deleteById(post.getId());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{id}/comments")
    public void createPostComment(@PathVariable("id") Integer id,
                                  @RequestBody Comment comment)
    {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException
                        ("No post found with id="+id));
        post.getComments().add(comment);
    }
    @DeleteMapping("/{postId}/comments/{commentId}")
    public void deletePostComment(@PathVariable("postId") Integer postId,
                                  @PathVariable("commentId") Integer
                                          commentId)
    {
        commentRepository.deleteById(commentId);
    }



    @ResponseBody
    @GetMapping(value = "/list", produces = "application/json")
    public PostGrid showPosts(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                              @RequestParam(value = "rows", required = false, defaultValue = "10") Integer rows,
                              @RequestParam(value = "sidx", required = false) String sortBy,
                              @RequestParam(value = "sord", required = false) String order){

        log.info("Current page {}, current rows {}", page, rows);


        // Constructs page request for current page
        // Note: page number for Spring Data JPA starts with 0,
        // while jqGrid starts with 1
        PageRequest pageRequest = null;
        pageRequest = PageRequest.of(page - 1, rows);

         Page<Post> postPage = postRepository.findAll(pageRequest);

        // Construct the grid data that will return as JSON data
        PostGrid postGrid = new PostGrid();
        postGrid.setCurrentPage(postPage.getNumber() + 1);
        postGrid.setTotalPages(postPage.getTotalPages());
        postGrid.setTotalRecords(postPage.getTotalElements());
        postGrid.setPostsData(postPage.stream().collect(Collectors.toList()));

        return postGrid;
    }

    @RequestMapping(value = "/list/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable("id") int id) {
        postRepository.deleteById(id);
    }
}
